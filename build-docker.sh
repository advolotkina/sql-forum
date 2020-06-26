docker build -t forum_db .
docker run -d --name forum_db -p 5555:5432 forum_db