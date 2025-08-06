import PrevButton from "../common/PrevButton";
import { useLocation } from "react-router-dom";

const AdminHeader = () => {
  const location = useLocation();

  const titleText = location.pathname.includes("register")
    ? "등록"
    : "수정/삭제";

  return (
    <header className="sticky top-0 z-50 w-full h-14 flex justify-between items-center px-3 py-5 bg-white">
      <div className="w-6 h-6 mr-2">
        <PrevButton />
      </div>
      <span className="text-xl font-semibold text-neutral-800">
        {titleText}
      </span>
      <div className="w-6 h-6 ml-2"></div>
    </header>
  );
};

export default AdminHeader;
