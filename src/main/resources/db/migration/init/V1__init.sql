ALTER SCHEMA auth OWNER TO docker;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: roles; Type: TABLE; Schema: auth; Owner: docker
--

CREATE TABLE auth.roles (
                            id bigint NOT NULL,
                            name character varying(255) NOT NULL
);


ALTER TABLE auth.roles OWNER TO docker;

--
-- Name: roles_id_seq; Type: SEQUENCE; Schema: auth; Owner: docker
--

CREATE SEQUENCE auth.roles_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE auth.roles_id_seq OWNER TO docker;

--
-- Name: roles_id_seq; Type: SEQUENCE OWNED BY; Schema: auth; Owner: docker
--

ALTER SEQUENCE auth.roles_id_seq OWNED BY auth.roles.id;


--
-- Name: user_roles; Type: TABLE; Schema: auth; Owner: docker
--

CREATE TABLE auth.user_roles (
                                 id bigint NOT NULL,
                                 user_id bigint NOT NULL,
                                 role_id bigint NOT NULL
);


ALTER TABLE auth.user_roles OWNER TO docker;

--
-- Name: user_roles_id_seq; Type: SEQUENCE; Schema: auth; Owner: docker
--

CREATE SEQUENCE auth.user_roles_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE auth.user_roles_id_seq OWNER TO docker;

--
-- Name: user_roles_id_seq; Type: SEQUENCE OWNED BY; Schema: auth; Owner: docker
--

ALTER SEQUENCE auth.user_roles_id_seq OWNED BY auth.user_roles.id;


--
-- Name: users; Type: TABLE; Schema: auth; Owner: docker
--

CREATE TABLE auth.users (
                            id bigint NOT NULL,
                            login character varying(180) NOT NULL,
                            password character varying(200) NOT NULL,
                            token character varying(255),
                            status character varying(50) NOT NULL
);


ALTER TABLE auth.users OWNER TO docker;

--
-- Name: users_id_seq; Type: SEQUENCE; Schema: auth; Owner: docker
--

CREATE SEQUENCE auth.users_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE auth.users_id_seq OWNER TO docker;

--
-- Name: users_id_seq; Type: SEQUENCE OWNED BY; Schema: auth; Owner: docker
--

ALTER SEQUENCE auth.users_id_seq OWNED BY auth.users.id;


--
-- Name: roles id; Type: DEFAULT; Schema: auth; Owner: docker
--

ALTER TABLE ONLY auth.roles ALTER COLUMN id SET DEFAULT nextval('auth.roles_id_seq'::regclass);


--
-- Name: user_roles id; Type: DEFAULT; Schema: auth; Owner: docker
--

ALTER TABLE ONLY auth.user_roles ALTER COLUMN id SET DEFAULT nextval('auth.user_roles_id_seq'::regclass);


--
-- Name: users id; Type: DEFAULT; Schema: auth; Owner: docker
--

ALTER TABLE ONLY auth.users ALTER COLUMN id SET DEFAULT nextval('auth.users_id_seq'::regclass);


--
-- Data for Name: roles; Type: TABLE DATA; Schema: auth; Owner: docker
--


--
-- Name: roles_id_seq; Type: SEQUENCE SET; Schema: auth; Owner: docker
--

SELECT pg_catalog.setval('auth.roles_id_seq', 1, false);


--
-- Data for Name: user_roles; Type: TABLE DATA; Schema: auth; Owner: docker
--



--
-- Name: user_roles_id_seq; Type: SEQUENCE SET; Schema: auth; Owner: docker
--

SELECT pg_catalog.setval('auth.user_roles_id_seq', 5, true);


--
-- Data for Name: users; Type: TABLE DATA; Schema: auth; Owner: docker
--



--
-- Name: users_id_seq; Type: SEQUENCE SET; Schema: auth; Owner: docker
--

SELECT pg_catalog.setval('auth.users_id_seq', 11, true);


--
-- Name: roles roles_pk; Type: CONSTRAINT; Schema: auth; Owner: docker
--

ALTER TABLE ONLY auth.roles
    ADD CONSTRAINT roles_pk PRIMARY KEY (id);


--
-- Name: user_roles user_roles_pk; Type: CONSTRAINT; Schema: auth; Owner: docker
--

ALTER TABLE ONLY auth.user_roles
    ADD CONSTRAINT user_roles_pk PRIMARY KEY (id);


--
-- Name: users users_pk; Type: CONSTRAINT; Schema: auth; Owner: docker
--

ALTER TABLE ONLY auth.users
    ADD CONSTRAINT users_pk PRIMARY KEY (id);


--
-- Name: roles_id_uindex; Type: INDEX; Schema: auth; Owner: docker
--

CREATE UNIQUE INDEX roles_id_uindex ON auth.roles USING btree (id);


--
-- Name: roles_name_uindex; Type: INDEX; Schema: auth; Owner: docker
--

CREATE UNIQUE INDEX roles_name_uindex ON auth.roles USING btree (name);


--
-- Name: user_roles_id_uindex; Type: INDEX; Schema: auth; Owner: docker
--

CREATE UNIQUE INDEX user_roles_id_uindex ON auth.user_roles USING btree (id);


--
-- Name: users_id_uindex; Type: INDEX; Schema: auth; Owner: docker
--

CREATE UNIQUE INDEX users_id_uindex ON auth.users USING btree (id);


--
-- Name: users_username_uindex; Type: INDEX; Schema: auth; Owner: docker
--

CREATE UNIQUE INDEX users_username_uindex ON auth.users USING btree (login);


--
-- Name: user_roles user_roles_roles_id_fk; Type: FK CONSTRAINT; Schema: auth; Owner: docker
--

ALTER TABLE ONLY auth.user_roles
    ADD CONSTRAINT user_roles_roles_id_fk FOREIGN KEY (role_id) REFERENCES auth.roles(id);


--
-- Name: user_roles user_roles_users_id_fk; Type: FK CONSTRAINT; Schema: auth; Owner: docker
--

ALTER TABLE ONLY auth.user_roles
    ADD CONSTRAINT user_roles_users_id_fk FOREIGN KEY (user_id) REFERENCES auth.users(id);


--
-- PostgreSQL database dump complete
--

