import React from 'react'

function UrlItem({url}) {
    const shortUrl = `http://localhost:8080/api/urls/${url.shortKey}`;

  return (
    <tr className='border-t'>
      <td className='p-2 text-blue-600 underline'>
        <a href={shortUrl} target="_blank" rel="noreferrer">
          {shortUrl}
        </a>
      </td>

      <td className='p-2 truncate max-w-xs'>
        {url.originalUrl}
      </td>

      <td className='p-2'>
        {url.createdBy ? url.createdBy.name : "Guest"}
      </td>

      <td className='p-2'>
        {new Date(url.createdAt).toLocaleString()}
      </td>

      <td className='p-2'>
        {new Date(url.expiresAt).toLocaleString()}
      </td>

      <td className='p-2'>
        {url.clickCount}
      </td>

      <td className='p-2'>
        {url.isPrivate ? "Private" : "Public"}
      </td>
    </tr>
  )
}

export default UrlItem
