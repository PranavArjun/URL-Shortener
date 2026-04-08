import { useState } from "react";

function UrlForm({onCreate}){
    const[url,setUrl] = useState("");
    const[loading,setLoading] = useState(false);

    const handleSubmit = async(e) =>{
        e.preventDefault();
        if(!url) return;

        try {
            setLoading(true);
            await onCreate(url);
            setUrl("");
        } catch (error) {
            console.error("Error creating URL:" ,error);
        }finally{
            setLoading(false);
        }
    }
    return (
        <div className="bg-white shadow-md rounded p-4 mb-6">
            <form onSubmit={handleSubmit} className="flex flex-col gap-3">
                <input type="text" placeholder="Enter your URL(https://example.com)" value={url} onChange={(e)=> setUrl(e.target.value)}
                className="border p-2 rounded focus:outline-none focus:ring-2 focus:ring-blue-400" />

                <button type="submit" disabled={loading} className="bg-blue-500 text-white py-2 rounded hover:bg-blue-600">
                    {loading ? "Creating..." : "Shorten URL"}
                </button>
            </form>
        </div>
    )
}

export default UrlForm;