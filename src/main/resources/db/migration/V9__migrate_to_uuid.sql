-- 1. Przygotowanie: Dodanie rozszerzenia dla UUID (jeśli Twoja wersja go wymaga)
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- 2. DODAWANIE NOWYCH KOLUMN (Tymczasowych)
-- Dodajemy nowe kolumny ID oraz kolumny dla kluczy obcych typu UUID
ALTER TABLE users ADD COLUMN new_id UUID DEFAULT uuid_generate_v4();
ALTER TABLE recipes ADD COLUMN new_id UUID DEFAULT uuid_generate_v4();
ALTER TABLE products ADD COLUMN new_id UUID DEFAULT uuid_generate_v4();
ALTER TABLE ingredients ADD COLUMN new_id UUID DEFAULT uuid_generate_v4();
ALTER TABLE verification_codes ADD COLUMN new_id UUID DEFAULT uuid_generate_v4();

-- Dodajemy kolumny na klucze obce (FK)
ALTER TABLE recipes ADD COLUMN new_user_id UUID;
ALTER TABLE products ADD COLUMN new_owner_id UUID;
ALTER TABLE products ADD COLUMN new_recipe_id UUID;
ALTER TABLE ingredients ADD COLUMN new_product_id UUID;
ALTER TABLE ingredients ADD COLUMN new_recipe_id UUID;
ALTER TABLE verification_codes ADD COLUMN new_user_id UUID;

-- 3. PRZEPISYWANIE RELACJI (Mapowanie danych)
-- Teraz "mówimy" bazie: tam gdzie w produktach owner_id = 5, wpisz nowy UUID użytkownika nr 5

-- Przepisujemy user_id w recipes
UPDATE recipes r SET new_user_id = u.new_id FROM users u WHERE r.user_id = u.id;

-- Przepisujemy owner_id i recipe_id w products
UPDATE products p SET new_owner_id = u.new_id FROM users u WHERE p.owner_id = u.id;
UPDATE products p SET new_recipe_id = r.new_id FROM recipes r WHERE p.recipe_id = r.id;

-- Przepisujemy powiązania w ingredients
UPDATE ingredients i SET new_product_id = p.new_id FROM products p WHERE i.product_id = p.id;
UPDATE ingredients i SET new_recipe_id = r.new_id FROM recipes r WHERE i.recipe_id = r.id;

-- Przepisujemy user_id w verification_codes
UPDATE verification_codes v SET new_user_id = u.new_id FROM users u WHERE v.user_id = u.id;

-- 4. CZYSZCZENIE STARYCH OGRANICZEŃ I KOLUMN
-- Najpierw usuwamy klucze obce (Constraints), które blokują usunięcie kolumn ID
ALTER TABLE verification_codes DROP CONSTRAINT IF EXISTS fk_verification_codes_on_user;
ALTER TABLE recipes DROP CONSTRAINT IF EXISTS fk_recipes_on_user;
ALTER TABLE products DROP CONSTRAINT IF EXISTS fk_products_on_owner;
ALTER TABLE products DROP CONSTRAINT IF EXISTS fk_products_on_recipe;
ALTER TABLE ingredients DROP CONSTRAINT IF EXISTS fk_ingredients_on_product;
ALTER TABLE ingredients DROP CONSTRAINT IF EXISTS fk_ingredients_on_recipe;

-- Teraz, gdy nic nie blokuje kolumn ID, możemy je bezpiecznie usunąć
ALTER TABLE ingredients DROP COLUMN product_id, DROP COLUMN recipe_id, DROP COLUMN id;
ALTER TABLE products DROP COLUMN owner_id, DROP COLUMN recipe_id, DROP COLUMN id;
ALTER TABLE recipes DROP COLUMN user_id, DROP COLUMN id;
ALTER TABLE verification_codes DROP COLUMN user_id, DROP COLUMN id;
ALTER TABLE users DROP COLUMN id;

-- 5. ZAMIANA NOWYCH KOLUMN NA DOCELOWE
-- Zmieniamy nazwy z new_id na id
ALTER TABLE users RENAME COLUMN new_id TO id;
ALTER TABLE recipes RENAME COLUMN new_id TO id;
ALTER TABLE products RENAME COLUMN new_id TO id;
ALTER TABLE ingredients RENAME COLUMN new_id TO id;
ALTER TABLE verification_codes RENAME COLUMN new_id TO id;

-- Zmieniamy nazwy kluczy obcych
ALTER TABLE recipes RENAME COLUMN new_user_id TO user_id;
ALTER TABLE products RENAME COLUMN new_owner_id TO owner_id;
ALTER TABLE products RENAME COLUMN new_recipe_id TO recipe_id;
ALTER TABLE ingredients RENAME COLUMN new_product_id TO product_id;
ALTER TABLE ingredients RENAME COLUMN new_recipe_id TO recipe_id;
ALTER TABLE verification_codes RENAME COLUMN new_user_id TO user_id;

-- 6. USTAWIANIE KLUCZY GŁÓWNYCH I OGRANICZEŃ (Primary Keys & Constraints)
ALTER TABLE users ADD PRIMARY KEY (id);
ALTER TABLE recipes ADD PRIMARY KEY (id);
ALTER TABLE products ADD PRIMARY KEY (id);
ALTER TABLE ingredients ADD PRIMARY KEY (id);
ALTER TABLE verification_codes ADD PRIMARY KEY (id);

-- Odbudowanie relacji (Foreign Keys)
ALTER TABLE recipes ADD CONSTRAINT fk_recipes_user FOREIGN KEY (user_id) REFERENCES users(id);
ALTER TABLE products ADD CONSTRAINT fk_products_owner FOREIGN KEY (owner_id) REFERENCES users(id);
ALTER TABLE products ADD CONSTRAINT fk_products_recipe FOREIGN KEY (recipe_id) REFERENCES recipes(id);
ALTER TABLE products ADD CONSTRAINT uc_products_recipe UNIQUE (recipe_id);
ALTER TABLE ingredients ADD CONSTRAINT fk_ingredients_product FOREIGN KEY (product_id) REFERENCES products(id);
ALTER TABLE ingredients ADD CONSTRAINT fk_ingredients_recipe FOREIGN KEY (recipe_id) REFERENCES recipes(id);
ALTER TABLE verification_codes ADD CONSTRAINT fk_codes_user FOREIGN KEY (user_id) REFERENCES users(id);