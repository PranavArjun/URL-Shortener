CREATE INDEX IF NOT EXISTS idx_created_at
    ON short_urls(created_at);

CREATE INDEX IF NOT EXISTS idx_is_private_created_at
    ON short_urls(is_private, created_at);

CREATE INDEX IF NOT EXISTS idx_created_by_created_at
    ON short_urls(created_by, created_at);