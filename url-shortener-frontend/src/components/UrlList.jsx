import React from 'react'
import UrlItem from './UrlItem';

function UrlList({urls}) {
    if(!urls || urls.length === 0){
        return(
            <p className='text-gray-500 mt-4 text-center'>
                No URLs yet
            </p>
        );
    }
  return (
    <div className='mt-6 overflow-x-auto w-full'>
        <table className='w-full bg-white shadow-md rounded'>
            <thead className='bg-gray-100'>
                <tr>
                    <th className='p-2 text-left'>Short URl</th>
                    <th className='p-2 text-left'>Original URl</th>
                    <th className='p-2 text-left'>Created By</th>
                    <th className='p-2 text-left'>Created At</th>
                    <th className='p-2 text-left'>Expiry</th>
                    <th className='p-2 text-left'>Clicks</th>
                    <th className='p-2 text-left'>Visibility</th>
                </tr>
            </thead>
            <tbody>
                {urls.map((url)=>(
                    <UrlItem key={url.id} url = {url}/>
                ))}
            </tbody>

        </table>
    </div>
  )
}

export default UrlList
