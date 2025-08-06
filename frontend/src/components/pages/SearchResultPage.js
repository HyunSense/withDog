import { useCallback, useEffect, useRef, useState } from "react";
import { getSearchCountPlaces, getSearchPlaces } from "../../apis/place";
import PlaceItem from "../place/PlaceItem";
import PrevButton from "../common/PrevButton";
import { useSearchParams } from "react-router-dom";
import SearchModal from "./SearchModal";

const SearchResultPage = () => {
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [places, setPlaces] = useState([]);
  const [page, setPage] = useState(0); // page는 0부터 시작
  const [hasMore, setHasMore] = useState(true);
  const loadMoreRef = useRef(null);
  const [count, setCount] = useState(null);
  const [searchParams] = useSearchParams();
  const searchParamsRef = useRef(searchParams);

  useEffect(() => {
    const fetchSearchCountPlaces = async () => {
      const params = {
        ...Object.fromEntries(searchParams.entries()),
      };
      const response = await getSearchCountPlaces(params);
      const countPlaces = response.data.data;
      setCount(countPlaces);
    };

    fetchSearchCountPlaces();
  }, [searchParams]);

  useEffect(() => {
    setPlaces([]);
    setHasMore(true);
    setPage(0);
  }, [searchParams]);

  // useRef를 통해 searchParams 최신값 유지
  useEffect(() => {
    searchParamsRef.current = searchParams;
  }, [searchParams]);

  const fetchSearchResult = useCallback(async (pageNum) => {
    try {
      const params = {
        ...Object.fromEntries(searchParamsRef.current.entries()),
        page: pageNum,
        size: 10,
      };
      const response = await getSearchPlaces(params);
      const { content, sliceInfo } = response.data.data;
      setPlaces((prev) => [...prev, ...content]);
      if (sliceInfo.last) {
        setHasMore(false);
      } else {
        setPage((prev) => prev + 1);
      }
    } catch (error) {
      console.log("검색 결과 오류 = ", error);
    }
  }, []);

  const onIntersection = useCallback(
    (entries) => {
      const firstEntity = entries[0];
      if (firstEntity.isIntersecting && hasMore) {
        fetchSearchResult(page);
      }
    },
    [hasMore, page, fetchSearchResult]
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

  return (
    <>
      <header className="sticky top-0 z-40 flex justify-between items-center w-full h-12 py-5 px-4 bg-white">
        <div className="w-6 h-6 mr-2">
          <PrevButton to="/" />
        </div>
        <span className="text-lg font-semibold text-neutral-800">검색</span>
        <div className="w-6 h-6 ml-2"></div>
      </header>
      <div
        className="sticky top-12 z-30 pb-5 p-4 bg-white"
        onClick={() => setIsModalOpen(true)}
      >
        <div className="flex items-center gap-x-2 w-full h-12 border border-solid border-gray-300 rounded-full bg-gray-50 px-5 cursor-pointer">
          <svg
            width="22"
            height="22"
            viewBox="0 0 24 24"
            fill="#4E5354"
            xmlns="http://www.w3.org/2000/svg"
          >
            <path
              fillRule="evenodd"
              clipRule="evenodd"
              d="M17.9375 10.9375C17.9375 14.8035 14.8035 17.9375 10.9375 17.9375C7.07151 17.9375 3.9375 14.8035 3.9375 10.9375C3.9375 7.07151 7.07151 3.9375 10.9375 3.9375C14.8035 3.9375 17.9375 7.07151 17.9375 10.9375ZM16.4088 17.4427C14.9303 18.6875 13.0215 19.4375 10.9375 19.4375C6.24308 19.4375 2.4375 15.6319 2.4375 10.9375C2.4375 6.24308 6.24308 2.4375 10.9375 2.4375C15.6319 2.4375 19.4375 6.24308 19.4375 10.9375C19.4375 13.0078 18.6973 14.9053 17.4672 16.3797L20.7225 19.6931C21.0128 19.9886 21.0086 20.4635 20.7131 20.7538C20.4176 21.044 19.9428 21.0398 19.6525 20.7444L16.4088 17.4427Z"
              fill="current"
            ></path>
          </svg>
          <span className="text-sm font-medium text-neutral-800">
            {searchParams.get("keyword") || "어디로 떠날까요?"}
          </span>
        </div>
      </div>
      <div className="text-sm font-medium px-4 text-neutral-800">
        검색결과 {count}건
      </div>
      <div className="mt-5 px-4 pb-6">
        <div className="flex flex-wrap gap-x-2 gap-y-5">
          {places.map((places) => (
            <PlaceItem
              item={places}
              key={places.id}
              perRow={2}
              to={`/places/${places.id}`}
            />
          ))}
        </div>
        {hasMore && <div ref={loadMoreRef}></div>}
      </div>
      {isModalOpen && <SearchModal onClose={() => setIsModalOpen(false)} />}
    </>
  );
};

export default SearchResultPage;
