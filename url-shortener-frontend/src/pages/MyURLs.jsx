import React, { useEffect, useState } from 'react'
import UrlForm from "../components/UrlForm";
import UrlList from "../components/UrlList";
import { getMyURLS } from '../api/urlApi';

function MyURLs() {
  const [urls, setUrls] = useState([]);
  const featchUrls = async () => {
    try {
      const res = await getMyURLS();
      setUrls(res.data);
    } catch (error) {
      console.log(error)
    }
  }
  useEffect(() => {
    featchUrls();
  }, []);

  // Create new URLs
  // const handleCreate = async (originalUrl) => {
  //     try {
  //         const res = await createShortUrl({ originalUrl });
  //         setUrls((prev) => [res.data, ...prev]);
  //     } catch (error) {
  //         console.error(error);
  //         throw error;
  //     }
  // }
  return (
    <div className="p-6">
      {/* <div className="max-w-3xl mx-auto mb-6">
        <UrlForm onCreate={handleCreate} />
      </div> */}

      {/* Table */}
      <div className="w-full">
        <UrlList urls={urls} />
      </div>

    </div>
  )
}

export default MyURLs
