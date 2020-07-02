create table roles (
    id serial primary key,
    name varchar(255)
);
create table users (
    id serial primary key,
    login varchar(255) not null unique,
    email varchar(255) not null unique,
    name varchar(255) not null,
    password_hash varchar(61),
    location varchar(255),
    profession varchar(255),
    extra_info text,
    signature text,
    user_pic varchar(255),
    registration_date timestamp default current_timestamp,
    is_banned boolean default false,
    comments_count integer default 0,
    last_comment timestamp,
    timezone_offset integer,
    role_id integer references roles(id)
);
create table groups(
    id serial primary key,
    name varchar(255) not null unique
);
create table themes(
    id serial primary key,
    name varchar(255) not null unique,
    comments_count integer default 0,
    last_comment timestamp,
    group_id integer references groups(id)
);
create table user_fav_themes(
    user_id integer references users(id),
    theme_id integer references themes(id)
);
create table theme_admins(
    theme_id integer references themes(id),
    admin_id integer references users(id)
);
create table topics(
    id serial primary key,
    name varchar(255) not null,
    description text not null,
    creation_datetime timestamp,
    views integer default 0,
    last_comment timestamp,
    comments_count integer default 0,
    theme_id integer references themes(id),
    author_id integer references users(id)
);
create table comments(
    id serial primary key,
    comment_text text,
    datetime timestamp default current_timestamp,
    topic_id integer references topics(id) on delete cascade,
    author_id integer references users(id)
);
create table comment_attachments(
    id serial primary key,
    url varchar(255),
    comment_id integer references comments(id) on delete cascade
);

CREATE OR REPLACE FUNCTION delete_user() RETURNS TRIGGER AS $user_delete$
    BEGIN
        delete from user_fav_themes where user_id = OLD.id;
        delete from theme_admins where admin_id = OLD.id;
        update topics set author_id = 1 where author_id = OLD.id;
        update comments set author_id = 1 where author_id = OLD.id;
        RETURN OLD;
    END;
$user_delete$ LANGUAGE plpgsql;

CREATE TRIGGER user_delete
BEFORE DELETE ON users
    FOR EACH ROW EXECUTE PROCEDURE delete_user();

CREATE OR REPLACE FUNCTION update_comments_count_and_datetime() RETURNS TRIGGER AS $comments_count_and_datetime_update$
    BEGIN
        IF (TG_OP = 'DELETE') THEN
            update topics set comments_count = comments_count - 1 where id = OLD.topic_id;
            update users set comments_count = comments_count - 1 where id = OLD.author_id;
            update themes set comments_count = comments_count - 1 where id = (select theme_id from topics where id = OLD.topic_id);
            RETURN OLD;
        ELSIF (TG_OP = 'INSERT') THEN
            update topics set comments_count = comments_count + 1, last_comment = NEW.datetime where id = NEW.topic_id;
            update users set comments_count = comments_count + 1, last_comment = NEW.datetime where id = NEW.author_id;
            update themes set comments_count = comments_count + 1, last_comment = NEW.datetime where id = (select theme_id from topics where id = NEW.topic_id);
            RETURN NEW;
        END IF;
        RETURN NULL;
    END;
$comments_count_and_datetime_update$ LANGUAGE plpgsql;

CREATE TRIGGER comments_count_and_datetime_update
AFTER INSERT OR DELETE ON comments
    FOR EACH ROW EXECUTE PROCEDURE update_comments_count_and_datetime();

SET TIME ZONE 'UTC';

insert into roles(name) values ('admin'), ('user');
insert into groups(name) values ('Виртуальные форумы'), ('Использование СУБД'), ('Дискуcсии'), ('Microsoft.NET'),
                                ('Программирование'), ('Web Технологии'), ('Администрирование ОС'),
                                ('SQL.RU');
insert into users(login, email, name, role_id) values ('deleted', 'del@del.com', 'DELETED', 2);

insert into users(login, email, name, role_id) values ('test', 'test@test.com', 'Test User', 2);
insert into users(login, email, name, role_id) values ('test2', 'test2@test.com', 'Test User #2', 2);
insert into users(login, email, name, role_id) values ('test3', 'test3@test.com', 'Test User #3', 2);

insert into themes (name, group_id) values ('PostgreSQL', 2);
insert into topics (name, description, theme_id, author_id) values ('File Streem в PostgreSQL',
                    'Знатоки PostgreSQL подскажите есть ли возможность в PostgreSQL создавать хранилище файлов, ' ||
                    'аналогичное filestreem в sqlserver?' ||
                    'Я вроде читал что есть механизм, а сейчас не могу найти.', 1, 2);
insert into comments (comment_text, topic_id, author_id) values
                      ('хотя там файлы можно хранить...но больще ничего нет?', 1, 3);
insert into comments (comment_text, topic_id, author_id) values
                      ('хотяdfdsfdsfdsfdsfdsfsdfdsfsdfsd', 1, 4);
insert into comments (comment_text, topic_id, author_id) values
                      ('FFFFFFFFFFFFFFFFFFFFFFF', 1, 4);
insert into comments (comment_text, topic_id, author_id) values
                      ('FFFFFFFFFFFFFFFFFfdfdfdfdFFFFFF', 1, 4);