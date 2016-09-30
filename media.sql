--
-- PostgreSQL database dump
--

-- Dumped from database version 9.5.4
-- Dumped by pg_dump version 9.5.4

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: animals; Type: TABLE; Schema: public; Owner: DroAlvarez
--

CREATE TABLE animals (
    id integer NOT NULL,
    name character varying,
    health character varying,
    age character varying,
    description character varying
);


ALTER TABLE animals OWNER TO "DroAlvarez";

--
-- Name: animals_id_seq; Type: SEQUENCE; Schema: public; Owner: DroAlvarez
--

CREATE SEQUENCE animals_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE animals_id_seq OWNER TO "DroAlvarez";

--
-- Name: animals_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: DroAlvarez
--

ALTER SEQUENCE animals_id_seq OWNED BY animals.id;


--
-- Name: rangers; Type: TABLE; Schema: public; Owner: DroAlvarez
--

CREATE TABLE rangers (
    id integer NOT NULL,
    name character varying,
    password character varying,
    badgenumber integer,
    contactinfo character varying
);


ALTER TABLE rangers OWNER TO "DroAlvarez";

--
-- Name: rangers_id_seq; Type: SEQUENCE; Schema: public; Owner: DroAlvarez
--

CREATE SEQUENCE rangers_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE rangers_id_seq OWNER TO "DroAlvarez";

--
-- Name: rangers_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: DroAlvarez
--

ALTER SEQUENCE rangers_id_seq OWNED BY rangers.id;


--
-- Name: sightings; Type: TABLE; Schema: public; Owner: DroAlvarez
--

CREATE TABLE sightings (
    id integer NOT NULL,
    location character varying,
    date timestamp without time zone,
    animalid integer,
    rangerid integer
);


ALTER TABLE sightings OWNER TO "DroAlvarez";

--
-- Name: sightings_id_seq; Type: SEQUENCE; Schema: public; Owner: DroAlvarez
--

CREATE SEQUENCE sightings_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE sightings_id_seq OWNER TO "DroAlvarez";

--
-- Name: sightings_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: DroAlvarez
--

ALTER SEQUENCE sightings_id_seq OWNED BY sightings.id;


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: DroAlvarez
--

ALTER TABLE ONLY animals ALTER COLUMN id SET DEFAULT nextval('animals_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: DroAlvarez
--

ALTER TABLE ONLY rangers ALTER COLUMN id SET DEFAULT nextval('rangers_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: DroAlvarez
--

ALTER TABLE ONLY sightings ALTER COLUMN id SET DEFAULT nextval('sightings_id_seq'::regclass);


--
-- Data for Name: animals; Type: TABLE DATA; Schema: public; Owner: DroAlvarez
--

COPY animals (id, name, health, age, description) FROM stdin;
\.


--
-- Name: animals_id_seq; Type: SEQUENCE SET; Schema: public; Owner: DroAlvarez
--

SELECT pg_catalog.setval('animals_id_seq', 1, false);


--
-- Data for Name: rangers; Type: TABLE DATA; Schema: public; Owner: DroAlvarez
--

COPY rangers (id, name, password, badgenumber, contactinfo) FROM stdin;
\.


--
-- Name: rangers_id_seq; Type: SEQUENCE SET; Schema: public; Owner: DroAlvarez
--

SELECT pg_catalog.setval('rangers_id_seq', 1, false);


--
-- Data for Name: sightings; Type: TABLE DATA; Schema: public; Owner: DroAlvarez
--

COPY sightings (id, location, date, animalid, rangerid) FROM stdin;
\.


--
-- Name: sightings_id_seq; Type: SEQUENCE SET; Schema: public; Owner: DroAlvarez
--

SELECT pg_catalog.setval('sightings_id_seq', 1, false);


--
-- Name: animals_pkey; Type: CONSTRAINT; Schema: public; Owner: DroAlvarez
--

ALTER TABLE ONLY animals
    ADD CONSTRAINT animals_pkey PRIMARY KEY (id);


--
-- Name: rangers_pkey; Type: CONSTRAINT; Schema: public; Owner: DroAlvarez
--

ALTER TABLE ONLY rangers
    ADD CONSTRAINT rangers_pkey PRIMARY KEY (id);


--
-- Name: sightings_pkey; Type: CONSTRAINT; Schema: public; Owner: DroAlvarez
--

ALTER TABLE ONLY sightings
    ADD CONSTRAINT sightings_pkey PRIMARY KEY (id);


--
-- Name: public; Type: ACL; Schema: -; Owner: DroAlvarez
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM "DroAlvarez";
GRANT ALL ON SCHEMA public TO "DroAlvarez";
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- PostgreSQL database dump complete
--

