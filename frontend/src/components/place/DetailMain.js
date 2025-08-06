import React from "react";
import DetailSlider from "./DetailSlider";

const handleCopyClipBoard = (text) => {
  try {
    navigator.clipboard.writeText(text);
    alert("클립보드에 복사되었습니다.");
  } catch (error) {
    alert("클립보드 복사에 실패하였습니다.");
  }
};

const DetailMain = ({ place }) => {
  const fullAddress = `${place.addressPart1} ${place.addressPart2}`.trim();

  return (
    <main>
      <DetailSlider imgs={place.placeImages} />
      <div className="flex flex-col gap-y-1 mt-5 px-4">
        <div className="flex justify-between items-center gap-x-2 mb-1">
          <span className="text-xl font-semibold text-neutral-800">
            {place.name}
          </span>
        </div>
        <div className="flex justify-between items-center gap-x-2 mb-1">
          <span className="text-sm font-medium text-neutral-800">
            {fullAddress}
          </span>
          <div className="flex justify-between items-center gap-x-2 mb-1">
            <span
              className="text-sm font-semibold text-sky-500 cursor-pointer"
              onClick={() => handleCopyClipBoard(fullAddress)}
            >
              복사하기
            </span>
            <a
              href={`https://map.naver.com/v5/search/${encodeURIComponent(
                fullAddress
              )}`}
              target="_blank"
              rel="noopener noreferrer"
              className="text-sm font-semibold text-sky-500 cursor-pointer"
            >
              지도보기
            </a>
          </div>
        </div>
        <div className="flex justify-between items-center gap-x-2 mb-1">
          <span className="text-sm font-medium text-neutral-800">
            {place.phone}
          </span>
          {place.phone && (
            <a
              href={`tel:${place.phone}`}
              className="text-sm font-semibold text-sky-500 cursor-pointer"
            >
              전화하기
            </a>
          )}
        </div>
        {place.reservationUrl && (
          <a
            href={place.reservationUrl}
            target="_blank"
            rel="noopener noreferrer"
            className="text-sm font-semibold text-emerald-500 cursor-pointer"
          >
            예약링크
          </a>
        )}
        <div className="mt-3 py-5">
          <p className="text-sm font-medium text-neutral-800">관련 블로그</p>
          <div className="grid grid-cols-2 gap-2 mt-4">
            {place.placeBlogs.map((blog, index) => (
              <a
                href={blog.blogUrl}
                target="_blank"
                rel="noopener noreferrer"
                key={index}
                className="text-neutral-800 cursor-pointer"
              >
                <div className="relative aspect-square overflow-hidden">
                  <img
                    referrerPolicy="no-referrer"
                    src={blog.imageUrl}
                    alt={blog.title}
                    className="object-cover w-full h-full rounded-lg"
                  />
                  <div className="absolute left-0 bottom-0 w-full p-1 bg-slate-50 bg-opacity-60 rounded-b-lg">
                    <p className="text-sm font-medium text-neutral-800 whitespace-nowrap overflow-hidden overflow-ellipsis">
                      {blog.title}
                    </p>
                    <p className="text-xs text-neutral-700 h-8 overflow-hidden overflow-ellipsis">
                      {blog.description}
                    </p>
                  </div>
                </div>
              </a>
            ))}
          </div>
        </div>
      </div>
    </main>
  );
};

export default DetailMain;
