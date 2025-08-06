import { React, useContext } from "react";
import withDogLogoUpdate from "../../assets/images/withdog-logo-300x80.png";
import { AuthContext } from "../auth/AuthContextProvider";
import { Link } from "react-router-dom";

const Header = () => {
  const { isLogin, memberInfo, logout } = useContext(AuthContext); // loading 제거

  return (
    <header className="flex items-center justify-between w-full mb-4 px-5 py-4">
      <Link to="/" className="w-36 h-auto">
        <img src={withDogLogoUpdate} alt="메인 로고" />
      </Link>
      {isLogin ? (
        <div className="flex items-center gap-x-1">
          {memberInfo.role === "ROLE_ADMIN" && (
            <Link
              to="/admin"
              className="flex justify-center items-center border border-solid border-gray-300 rounded-md cursor-pointer bg-white px-2 py-1"
            >
              <span className="text-xs font-medium text-black">관리자</span>
            </Link>
          )}
          <Link
            to="/mypage/bookmark"
            className="flex justify-center items-center border border-solid border-gray-300 rounded-md cursor-pointer bg-white px-2 py-1"
          >
            <span className="text-xs font-medium text-black">북마크</span>
          </Link>
          <Link
            onClick={logout}
            className="flex justify-center items-center border border-solid border-gray-300 rounded-md cursor-pointer bg-white px-2 py-1"
          >
            <span className="text-xs font-medium text-black">로그아웃</span>
          </Link>
        </div>
      ) : (
        <Link
          to="/login"
          className="flex justify-center items-center border border-solid border-gray-300 rounded-md cursor-pointer bg-white px-2 py-1"
        >
          <span className="text-xs font-medium text-black">로그인</span>
        </Link>
      )}
    </header>
  );
};

export default Header;
