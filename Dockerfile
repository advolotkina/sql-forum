FROM postgres
ENV POSTGRES_PASSWORD postgres
ENV POSTGRES_DB forumdb
COPY init.sql /docker-entrypoint-initdb.d/