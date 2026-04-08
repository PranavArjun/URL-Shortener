import { useState } from "react";
import UrlForm from "../components/UrlForm";
import { createShortUrl } from "../api/urlApi";
function Home() {
    const [urls, setUrls] = useState([]);

    const handleCreate = async (originalUrl) => {
        try {
            const res = await createShortUrl({ originalUrl });
            setUrls((prev) => [res.data, ...prev]);
        } catch (error) {
            console.error(error);
            throw error;
        }
    }
    return (
        <div className="p-6 max-w-3xl mx-auto">
            <UrlForm onCreate={handleCreate} />
        </div>
    )
}

export default Home;