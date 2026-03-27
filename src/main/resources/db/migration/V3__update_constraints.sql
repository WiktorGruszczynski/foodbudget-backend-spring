ALTER TABLE spring_session_attributes
    DROP CONSTRAINT spring_session_attributes_session_primary_id_fkey;

ALTER TABLE products
    ALTER COLUMN density DROP NOT NULL;