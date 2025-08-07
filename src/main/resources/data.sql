create extension if not exists "uuid-ossp";

-- Enable UUID support
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- Timezones
INSERT INTO TIMEZONES (name, utc_offset) VALUES
  ('Asia/Tashkent', '+05:00'),
  ('Europe/Berlin', '+01:00'),
  ('Asia/Kolkata', '+05:30');

-- Roles
INSERT INTO ROLES (name) VALUES
  ('Frontend Developer'),
  ('Backend Developer'),
  ('ML Engineer'),
  ('Data Scientist');

-- Skills
INSERT INTO SKILLS (title) VALUES
  ('JavaScript'),
  ('React'),
  ('Python'),
  ('TensorFlow'),
  ('PostgreSQL'),
  ('Data Analysis');

-- Users
INSERT INTO USERS (name, email, password, timezone_id)
VALUES
  ('Alice', 'alice@example.com', 'hashedpass1', (SELECT id FROM timezones WHERE name = 'Asia/Tashkent')),
  ('Bob', 'bob@example.com', 'hashedpass2', (SELECT id FROM timezones WHERE name = 'Europe/Berlin')),
  ('Charlie', 'charlie@example.com', 'hashedpass3', (SELECT id FROM timezones WHERE name = 'Asia/Kolkata')),
  ('Diana', 'diana@example.com', 'hashedpass4', (SELECT id FROM timezones WHERE name = 'Asia/Tashkent')),
  ('Eve', 'eve@example.com', 'hashedpass5', (SELECT id FROM timezones WHERE name = 'Asia/Kolkata'));

-- User Roles
INSERT INTO USER_ROLES (user_id, role_id)
SELECT u.id, r.id
FROM users u, roles r
WHERE (u.name = 'Alice' AND r.name = 'Frontend Developer')
   OR (u.name = 'Bob' AND r.name = 'Backend Developer')
   OR (u.name = 'Charlie' AND r.name = 'ML Engineer')
   OR (u.name = 'Diana' AND r.name = 'Data Scientist')
   OR (u.name = 'Eve' AND r.name IN ('ML Engineer', 'Backend Developer'));

-- User Skills
INSERT INTO USER_SKILLS (user_id, skill_id)
SELECT u.id, s.id
FROM users u, skills s
WHERE (u.name = 'Alice' AND s.title IN ('JavaScript', 'React'))
   OR (u.name = 'Bob' AND s.title = 'PostgreSQL')
   OR (u.name = 'Charlie' AND s.title = 'TensorFlow')
   OR (u.name = 'Diana' AND s.title = 'Data Analysis')
   OR (u.name = 'Eve' AND s.title IN ('TensorFlow', 'PostgreSQL'));

-- Teams
INSERT INTO TEAMS (name, description, owner_id, status)
VALUES
  ('AI Vision Team', 'Building a real-time image classification app.',
    (SELECT id FROM users WHERE name = 'Alice'), 'open'),
  ('Data Wizards', 'Working on data insights for public health.',
    (SELECT id FROM users WHERE name = 'Diana'), 'open');

-- Team Requirements
INSERT INTO TEAM_REQUIREMENTS (team_id, role_id, min_count, max_count)
SELECT t.id, r.id, req.min, req.max
FROM teams t, roles r,
     (VALUES
       ('AI Vision Team', 'ML Engineer', 1, 2),
       ('AI Vision Team', 'Data Scientist', 1, 1),
       ('Data Wizards', 'Backend Developer', 1, 1)
     ) AS req(team_name, role_name, min, max)
WHERE t.name = req.team_name AND r.name = req.role_name;

-- Team Members
INSERT INTO TEAM_MEMBERS (team_id, user_id, role_id, approved, joined_at)
SELECT t.id, u.id, r.id, true, now()
FROM users u, teams t, roles r
WHERE (u.name = 'Alice' AND t.name = 'AI Vision Team' AND r.name = 'Frontend Developer')
   OR (u.name = 'Charlie' AND t.name = 'AI Vision Team' AND r.name = 'ML Engineer');
