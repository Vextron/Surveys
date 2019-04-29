DROP TABLE IF EXISTS "answers";
DROP TABLE IF EXISTS "questions";
DROP TABLE IF EXISTS "surveys";
DROP TABLE IF EXISTS "submission_codes";

create table surveys (
    id Serial PRIMARY KEY,
    numans INTEGER
);

create table questions (

    id Serial PRIMARY KEY,
    surveyid INTEGER REFERENCES surveys(id) ON DELETE CASCADE,
    question VARCHAR
);

create table submission_codes (
    codesub Serial PRIMARY KEY
);

create table answers (

    id Serial PRIMARY KEY,
    questionid INTEGER REFERENCES questions(id) ON DELETE CASCADE,
    surveyid INTEGER REFERENCES surveys(id) ON DELETE CASCADE,
    codsub INTEGER REFERENCES submission_codes(codesub) ON DELETE CASCADE,
    value INTEGER
);