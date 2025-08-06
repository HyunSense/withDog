import { useEffect, useState } from "react";
import { deleteSelectedBookmarks, getAllBookmarks } from "../../apis/place";

const BookmarkMain = () => {
  const [bookmarkedPlaces, setBookmarkedPlaces] = useState([]);
  const [selectedMarks, setSelectedMarks] = useState([]);

  const handleBookmarkChange = (id) => {
    
    setSelectedMarks((prev) =>
      prev.includes(id) ? prev.filter((item) => item !== id) : [...prev, id]
    );
  };

  useEffect(() => {
    fetchBookmarks();
  }, []);

  const fetchBookmarks = async () => {
    try {
      const response = await getAllBookmarks();
      const apiResponse = response.data;
      setBookmarkedPlaces(apiResponse.data);
      setSelectedMarks([]);
    } catch (error) {
      console.error("fetchBookmarks error = ", error);
    }
  };

  const handleDeleteButtonClick = () => {
    if (selectedMarks.length === 0) {
      return;
    }
    fetchBookmarkDelete(selectedMarks);
  };

  const fetchBookmarkDelete = async (ids) => {
    try {
      const params = { ids: ids.join(",") };
      await deleteSelectedBookmarks(params);

      fetchBookmarks();
    } catch (error) {
      console.error("fetchBookmarkDelete error = ", error);
    }
  };

  return (
    <main className="relative flex flex-col grow shrink-0 basis-0 bg-white w-full h-full pt-5 px-4 overflow-y-auto text-neutral-800">
      <p className="font-semibold">북마크한 장소 {bookmarkedPlaces.length}</p>
      <div className="flex-1 overflow-y-auto px-4 mt-5 pb-5 hide-scrollbar">
        <div className="flex flex-col gap-y-6 pb-24">
          {bookmarkedPlaces.map((place) => (
            <div className="flex justify-between items-center" key={place.id}>
              <div className="flex items-center w-full gap-x-2">
                <img
                  className="w-12 h-12 border border-gray-300 rounded-full object-cover"
                  src={place.thumbnailUrl}
                  alt="장소 썸네일"
                />
                <div className="flex flex-col justify-center gap-y-1">
                  <span className="text-sm font-medium">{place.name}</span>
                  <span className="text-xs font-medium text-gray-500">
                    {place.address}
                  </span>
                </div>
              </div>
              <input
                className="hidden"
                type="checkbox"
                id={`bookmark-${place.id}`}
                checked={selectedMarks.includes(place.id)}
                onChange={() => handleBookmarkChange(place.id)}
              />
              <label
                className="cursor-pointer"
                htmlFor={`bookmark-${place.id}`}
              >
                <svg
                  width="24"
                  height="24"
                  viewBox="0 0 20 20"
                  fill="#DDE2E3"
                  xmlns="http://www.w3.org/2000/svg"
                >
                  <rect
                    x="0.5"
                    y="0.5"
                    width="19"
                    height="19"
                    rx="9.5"
                    fill="current"
                  ></rect>
                  <path
                    fillRule="evenodd"
                    clipRule="evenodd"
                    d="M14.6549 6.27069C15.0723 6.6324 15.1174 7.26396 14.7557 7.68131L9.63127 13.5941C9.19265 14.1002 8.40738 14.1002 7.96876 13.5941L5.24433 10.4505C4.88262 10.0332 4.92773 9.40163 5.34509 9.03992C5.76244 8.67822 6.394 8.72333 6.7557 9.14068L8.80002 11.4995L13.2443 6.37145C13.606 5.9541 14.2376 5.90899 14.6549 6.27069Z"
                    fill="white"
                  ></path>
                </svg>
              </label>
            </div>
          ))}
        </div>
      </div>
      <div className="absolute left-0 bottom-0 w-full pt-3 pb-9 px-4 border-t bg-white">
        <button
          className={`flex justify-center items-center w-full h-12 rounded-lg font-semibold text-white
          ${
            selectedMarks.length > 0
              ? "bg-[#022733] cursor-pointer"
              : "bg-[#dde2e3] cursor-default"
          }`}
          onClick={() => {
            handleDeleteButtonClick();
          }}
          disabled={selectedMarks.length === 0}
        >
          북마크 삭제
        </button>
      </div>
    </main>
  );
};

export default BookmarkMain;
