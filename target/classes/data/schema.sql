create table groups
(
    created_at timestamp(6),
    id         bigserial,
    updated_at timestamp(6),
    name       varchar(255) not null,
    primary key (id)
);


create table status
(
    created_at   timestamp(6),
    id           bigserial,
    updated_at   timestamp(6),
    name         varchar(255) not null,
    time_expired varchar(255) not null,
    groups_id    bigint       not null,
    foreign key (groups_id) references groups (id),
    primary key (id)
);


create table question
(
    savol_ball integer      not null,
    created_at timestamp(6),
    id         bigserial,
    status_id  bigint       not null,
    updated_at timestamp(6),
    test_value text         not null,
    title      varchar(255) not null,
    primary key (id),
    constraint UKaw7cbybkgs8mwenqk0jdix7qm unique (status_id, title),
    foreign key (status_id) references status (id) on delete cascade
);

create table answer
(
    is_correct  boolean,
    created_at  timestamp(6),
    id          bigserial    not null,
    question_id bigint       not null,
    updated_at  timestamp(6),
    value       varchar(255) not null,
    primary key (id),
    foreign key (question_id) references question (id) on delete cascade
);


create table users
(
    ball                       integer,
    enabled                    boolean,
    is_account_non_expired     boolean,
    is_account_non_locked      boolean,
    is_credentials_non_expired boolean,
    is_finished                boolean,
    updated_at                 time(6),
    created_at                 timestamp(6),
    status_id                  bigint,
    id                         uuid         not null,
    email                      text         NOT NULL UNIQUE CHECK (email ~ '^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$'),
    email_code                 varchar(255),
    name                       varchar(255) not null,
    password                   varchar(255) not null,
    groups_id                  bigint,
    surname                    varchar(255) not null,
    system_role_name           varchar(255),
    primary key (id),
    foreign key (status_id) references status (id) on
        delete
        set null,
    foreign key (groups_id) references groups (id) on delete set null
);

create table user_data
(
    answer_id   bigint,
    created_at  timestamp(6),
    id          bigserial not null,
    question_id bigint,
    updated_at  timestamp(6),
    user_id     uuid,
    primary key (id),
    foreign key (answer_id) references answer (id) on delete set null,
    foreign key (question_id) references question (id) on delete cascade,
    foreign key (user_id) references users (id) on delete cascade
);

create table language
(
    created_at                timestamp(6),
    id                        bigserial,
    updated_at                timestamp(6),
    programming_language_name varchar(255) not null,
    primary key (id)
);
create table interview_question_answer
(
    created_at      timestamp(6),
    id              bigserial,
    updated_at      timestamp(6),
    language_id     bigint not null,
    question_count  varchar(255),
    question_answer varchar(255),
    foreign key (language_id) references language (id) on delete cascade,
    primary key (id)
);



