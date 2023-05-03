insert into users (username, enabled, password, authority_id)
values ('root', true, '$2a$10$wU6gZCZvsnrN8u7B7vdYpuaowRlhaA3vMrm2gBjKijvN72YwwAP2K',
        (select id from authorities where authority = 'ROLE_ADMIN'));