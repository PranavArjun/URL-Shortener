import { useEffect, useRef, useState } from "react";
import UrlForm from "../components/UrlForm";
import { createShortUrl, getAllUrls } from "../api/urlApi";
import UrlList from "../components/UrlList";
import Pagination from "../components/Pagination";
import Loading from "../components/Loading";

function Home() {
    const [urls, setUrls] = useState([]);
    const [page, setPage] = useState(0);
    const [totalPages, setTotalPages] = useState(1);
    const [loading, setLoading] = useState(true);

    const fetched = useRef(false);

    const fetchUrls = async (pageNo = 0) => {
        try {
            setLoading(true);

            const res = await getAllUrls(pageNo);

            setUrls(res.data.content);
            setPage(res.data.number);
            setTotalPages(res.data.totalPages);

        } catch (error) {
            console.error("Error fetching URLs");
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        if (fetched.current) return;

        fetched.current = true;
        fetchUrls(0);
    }, []);

    const handleCreate = async (originalUrl) => {
        try {
            await createShortUrl({ originalUrl });
            fetchUrls(0);
        } catch (error) {
            console.error(error);
            throw error;
        }
    };

    return (
        <div className="min-h-screen flex flex-col p-6">

            <div className="max-w-3xl mx-auto mb-6 w-full">
                <UrlForm onCreate={handleCreate} />
            </div>

            <div className="grow w-full">
                {loading ? <Loading /> : <UrlList urls={urls} />}
            </div>

            {!loading && (
                <Pagination
                    page={page}
                    totalPages={totalPages}
                    onPageChange={fetchUrls}
                />
            )}

        </div>
    );
}

export default Home;