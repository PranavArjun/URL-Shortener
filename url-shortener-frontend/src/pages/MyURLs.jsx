import { useEffect, useRef, useState } from "react";
import UrlList from "../components/UrlList";
import Pagination from "../components/Pagination";
import Loading from "../components/Loading";
import { getMyURLS } from "../api/urlApi";

function MyURLs() {
    const [urls, setUrls] = useState([]);
    const [page, setPage] = useState(0);
    const [totalPages, setTotalPages] = useState(1);
    const [loading, setLoading] = useState(true);

    const fetched = useRef(false);

    const fetchUrls = async (pageNo = 0) => {
        try {
            setLoading(true);

            const res = await getMyURLS(pageNo);

            setUrls(res.data.content);
            setPage(res.data.number);
            setTotalPages(res.data.totalPages);

        } catch (error) {
            console.error(error);
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        if (fetched.current) return;

        fetched.current = true;
        fetchUrls(0);
    }, []);

    return (
        <div className="min-h-screen flex flex-col p-6">

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

export default MyURLs;