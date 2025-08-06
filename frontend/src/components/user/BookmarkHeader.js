import PrevButton from "../common/PrevButton";

const BookmarkHeader = () => {
  return (
    <header className="sticky top-0 z-50 w-full h-12 flex justify-between items-center px-3 pt-5 mb-5 bg-white">
      <div className="w-6 h-6 mr-2">
        <PrevButton to="/" />
      </div>
      <p className="text-xl font-semibold text-neutral-800">북마크</p>
      <div className="w-6 h-6 ml-2"></div>
    </header>
  );
};

export default BookmarkHeader;
