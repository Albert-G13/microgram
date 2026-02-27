INSERT INTO roles (role) VALUES ('USER');

INSERT INTO users_table (login, email, password, name, info, follower_count, following_count, enabled, role_id)
VALUES
    ('johndoe', 'john@example.com', '$2a$12$WB2YUbFcCN0tm44SBcKUjua9yiFBsfB3vW02IjuwzY7HGtlQIKzy2', 'John Doe', 'Love photography', 0, 0, true, (select id from roles where role = 'USER')),
    ('janedoe', 'jane@example.com', '$2a$12$WB2YUbFcCN0tm44SBcKUjua9yiFBsfB3vW02IjuwzY7HGtlQIKzy2', 'Jane Doe', 'Traveler & Blogger', 0, 0, true, (select id from roles where role = 'USER'));

INSERT INTO publications (image, description, commentary_count, likes_count, date_of_publication, author_id)
VALUES
    ('post1.jpg', 'My first photo!', 1, 1, CURRENT_TIMESTAMP, (select id from users_table where email = 'john@example.com')),
    ('post2.jpg', 'Beautiful sunset', 0, 0, CURRENT_TIMESTAMP, (select id from users_table where email = 'jane@example.com'));

INSERT INTO likes (user_id, publication_id) VALUES (1, 1);

INSERT INTO commentaries (content, date_of_publication, publication_id, author_id)
VALUES ('Great shot!', CURRENT_TIMESTAMP, 1, (select id from users_table where email = 'jane@example.com'));

INSERT INTO follows (follower_id, following_id) VALUES (2, 1)