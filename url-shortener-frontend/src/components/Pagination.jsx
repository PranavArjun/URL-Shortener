function Pagination({ page, totalPages, onPageChange }) {
  const current = page + 1; // backend 0-based → UI 1-based

  let pages = [];

  // If pages are 3 or less, show all
  if (totalPages <= 3) {
    pages = Array.from({ length: totalPages }, (_, i) => i + 1);
  }

  // First page
  else if (current === 1) {
    pages = [1, 2, 3];
  }

  // Last page
  else if (current === totalPages) {
    pages = [totalPages - 2, totalPages - 1, totalPages];
  }

  // Middle pages
  else {
    pages = [current, current + 1];

    // keep max 3 buttons
    if (current + 2 < totalPages) {
      pages.push(current + 2);
    } else {
      pages.unshift(current - 1);
    }
  }

  // Remove invalid numbers
  pages = pages.filter((n) => n >= 1 && n <= totalPages);

  const lastVisible = pages[pages.length - 1];

  return (
    <div className="flex justify-center items-center gap-2 mt-8 flex-wrap">

      {/* Prev */}
      <button
        disabled={current === 1}
        onClick={() => onPageChange(page - 1)}
        className="px-4 py-2 rounded-lg border border-gray-300 bg-white hover:bg-gray-100 disabled:opacity-40 disabled:cursor-not-allowed transition"
      >
        ‹
      </button>

      {/* Page Numbers */}
      {pages.map((num) => (
        <button
          key={num}
          onClick={() => onPageChange(num - 1)}
          className={`w-10 h-10 rounded-lg text-sm font-medium transition ${
            num === current
              ? "bg-blue-600 text-white shadow"
              : "bg-white border border-gray-300 hover:bg-gray-100"
          }`}
        >
          {num}
        </button>
      ))}

      {/* Dots + Last Page */}
      {lastVisible < totalPages && (
        <>
          {lastVisible < totalPages - 1 && (
            <span className="px-2 text-gray-500 select-none">...</span>
          )}

          <button
            onClick={() => onPageChange(totalPages - 1)}
            className={`w-10 h-10 rounded-lg text-sm font-medium transition ${
              current === totalPages
                ? "bg-blue-600 text-white shadow"
                : "bg-white border border-gray-300 hover:bg-gray-100"
            }`}
          >
            {totalPages}
          </button>
        </>
      )}

      {/* Next */}
      <button
        disabled={current === totalPages}
        onClick={() => onPageChange(page + 1)}
        className="px-4 py-2 rounded-lg border border-gray-300 bg-white hover:bg-gray-100 disabled:opacity-40 disabled:cursor-not-allowed transition"
      >
        ›
      </button>
    </div>
  );
}

export default Pagination;