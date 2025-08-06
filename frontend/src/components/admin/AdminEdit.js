import AdminEditItem from "./AdminEditItem";
import { useCallback, useEffect, useRef, useState } from "react";
import { deletePlaces, getAllPlaces, getCountPlaces } from "../../apis/place";

const AdminEdit = () => {
  const [places, setPlaces] = useState([]);
  const [selectedItems, setSelectedItems] = useState([]);
  const [page, setPage] = useState(0);
  const [hasMore, setHasMore] = useState(true);
  const [count, setCount] = useState(null);
  const loadMoreRef = useRef(null);

  const fetchCountPlaces = async () => {
    const response = await getCountPlaces();
    const countPlaces = response.data.data;
    setCount(countPlaces);
  };

  useEffect(() => {
    fetchCountPlaces();
  }, []);

  const handleCheckboxChange = (id) => {
    setSelectedItems((prev) =>
      prev.includes(id) ? prev.filter((item) => item !== id) : [...prev, id]
    );
  };

  const handleDelete = () => {
    if (selectedItems.length === 0) {
      return;
    }

    const isConfirmed = window.confirm(
      `${selectedItems.length}개의 장소를 삭제 하시겠습니까?`
    );

    if (isConfirmed) {
      fetchDeletePlaces(selectedItems);
      setCount((prev) => prev - selectedItems.length);
    }
  };

  const onIntersection = useCallback(
    (entries) => {
      const firstEntity = entries[0];
      if (firstEntity.isIntersecting && hasMore) {
        fetchMorePlaces(page);
      }
    },
    [hasMore, page]
  );

  useEffect(() => {
    const observer = new IntersectionObserver(onIntersection, { threshold: 1 });

    const currentRef = loadMoreRef.current;

    if (currentRef) {
      observer.observe(currentRef);
    }

    return () => {
      if (currentRef) {
        observer.unobserve(currentRef);
      }
    };
  }, [onIntersection]);

  const fetchDeletePlaces = async (ids) => {
    try {
      const params = { ids: ids.join(",") };
      await deletePlaces(params);
      setPlaces((prev) => prev.filter((place) => !ids.includes(place.id)));
      setSelectedItems([]);
    } catch (error) {
      console.error("fetchDeletePalces error = ", error);
    }
  };

  const fetchMorePlaces = async (page) => {
    try {
      const response = await getAllPlaces({ page: page });
      const { content, sliceInfo } = response.data.data;

      setPlaces((prev) => [...prev, ...content]);

      if (sliceInfo.last) {
        setHasMore(false);
      } else {
        setPage((prev) => prev + 1);
      }
    } catch (error) {
      console.error("AdminEdit fetchMorePlaces error = ", error);
    }
  };

  return (
    <>
      <div className="flex flex-col w-full h-full bg-white">
        <div className="h-full grow shrink-0 basis-0 overflow-y-auto hide-scrollbar">
          <div className="flex items-center px-4 py-2 mb-2 gap-x-0.5 bg-white border-t border-b border-gray-100">
            <span className="text-sm font-medium">등록된 장소 {count}건</span>
          </div>
          <div className="flex flex-col px-4 gap-y-6 bg-white">
            {places.map((place) => (
              <AdminEditItem
                key={place.id}
                item={place}
                checked={selectedItems.includes(place.id)}
                onChange={() => handleCheckboxChange(place.id)}
              />
            ))}
          </div>
          {hasMore && <div ref={loadMoreRef}></div>}
        </div>
      </div>
      <div className="sticky bottom-0 w-full px-4 pt-3 pb-8 border-t bg-white">
        <button
          className={`flex justify-center items-center w-full h-12 rounded-lg font-semibold text-white
          ${
            selectedItems.length > 0
              ? "bg-[#022733] cursor-pointer"
              : "bg-[#dde2e3] cursor-default"
          }`}
          onClick={() => handleDelete()}
          disabled={selectedItems.length === 0}
        >
          장소 삭제
        </button>
      </div>
    </>
  );
};

export default AdminEdit;
