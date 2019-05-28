--D\class\jdbc\jdbcTest\script.sql

--주소록 테이블
CREATE TABLE tblAddress
(
    seq NUMBER PRIMARY KEY,
    name VARCHAR2(30) NOT NULL,
    age NUMBER(3) NOT NULL,
    tel VARCHAR2(15) NULL,
    address VARCHAR2(100) NOT NULL,
    regdate DATE DEFAULT SYSDATE NOT NULL

);

CREATE SEQUENCE address_seq;

INSERT INTO tblAddress (seq, name, age, tel, address, regdate)
    VALUES (address_seq.nextVal, '홍길동', 20, '010-1234-5678', '서울시 강남구 역삼동', DEFAULT);

COMMIT;

SELECT * FROM tbladdress;

ROLLBACK;








DROP TABLE tblMemo;
DROP SEQUENCE memo_seq;
-- 메모장
CREATE TABLE tblMemo
(
    seq NUMBER PRIMARY KEY,
    name VARCHAR2(30) NOT NULL,
    memo VARCHAR2(1000) NOT NULL,
    priority NUMBER(1) NOT NULL CHECK(priority between 1 and 3), --중요도(1.중요 ~ 3.안중요)
    regdate DATE DEFAULT SYSDATE NOT NULL

);

CREATE SEQUENCE memo_seq;

INSERT INTO tblMemo (seq, name, memo, priority, regdate)
    VALUES (memo_seq.nextVal, '홍길동', '메모입니다.', 2, default);

COMMIT;

SELECT * FROM tblmemo;


UPDATE tblMemo SET regdate = regdate - 1 WHERE seq = 1;


CREATE OR REPLACE VIEW vwMemo
AS
SELECT 
    seq, name, memo,
    CASE
        WHEN TO_CHAR(regdate, 'yyyymmdd') = TO_CHAR(SYSDATE, 'yyyymmdd') 
            THEN TO_CHAR(regdate, 'hh24:mi:ss')
        ELSE TO_CHAR(regdate, 'yyyy-mm-dd')    
    END AS "regtime",
    CASE
        WHEN priority = 1 THEN '☆☆☆'
        WHEN priority = 2 THEN '☆☆'        
        WHEN priority = 3 THEN '☆'
    END AS "priorityLabel"
    
FROM tblMemo
    ORDER BY seq DESC;

UPDATE tblMemo SET
    memo = '수정된 메모'
        WHERE seq = 1;

UPDATE tblMemo SET
    priority = 1
        WHERE seq = 1;

UPDATE tblMemo SET
    memo = '수정된 메모',priority = 1
        WHERE seq = 1;

UPDATE tblMemo SET
    name = '홍길동', memo = '메모', priority = 1
        WHERE seq = 1;


SELECT * FROM tblAddress;

SELECT 
    buseo, 
    jikwi, 
    (SELECT name FROM tblinsa WHERE buseo = a.buseo AND jikwi = '부장' AND rownum = 1) 
FROM tblinsa a WHERE jikwi = '부장' GROUP BY buseo,jikwi
    ORDER BY buseo,jikwi;

SELECT * FROM tblinsa
    WHERE buseo = ?
        AND jikwi <> '부장'























