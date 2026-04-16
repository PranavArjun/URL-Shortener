import { useState } from "react";

function UrlForm({ onCreate }) {
    const [url, setUrl] = useState("");
    const [loading, setLoading] = useState(false);
    const[error,setError] = useState("");

    const handleSubmit = async (e) => {
        e.preventDefault();
        if (!url) return;

        try {
            setLoading(true);
            await onCreate(url);
            setUrl("");
        } catch (error) {
            console.log("ERROR:", error);
            console.log("DATA:",error.response.data);
            setError(error.response?.data?.originalUrl || "URL form Error");
        } finally {
            setLoading(false);
        }
    }
    return (
        <div className="bg-white shadow-md rounded p-4 mb-6">
            <form onSubmit={handleSubmit} className="flex gap-3">

                <input
                    type="text"
                    placeholder="Enter your URL (https://example.com)"
                    value={url}
                    onChange={(e) => setUrl(e.target.value)}
                    className={`flex-1 border p-2 rounded focus:outline-none focus:ring-2 ${error ? "border-red-500" : "focus:ring-blue-400"
                        }`}
                />

                <button
                    type="submit"
                    disabled={loading}
                    className="bg-blue-500 text-white px-4 rounded hover:bg-blue-600 disabled:opacity-50"
                >
                    {loading ? "..." : "Shorten"}
                </button>

            </form>

            {/* Error message */}
            {error && (
                <p className="text-red-500 text-sm mt-2">{error}</p>
            )}
        </div>
    )
}

export default UrlForm;