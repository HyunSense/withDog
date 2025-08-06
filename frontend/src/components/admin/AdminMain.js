import { Outlet, useLocation, useNavigate } from "react-router-dom";

const AdminMain = () => {
  const location = useLocation();
  const navigate = useNavigate();

  return (
    <main className="flex flex-col w-full h-full">
      <nav className="flex items-center pt-5 px-2 mb-5 gap-x-1">
        <button
          className={`py-0.5 px-2 font-medium bg-none rounded-lg cursor-pointer
        ${
          location.pathname === "/admin/register"
            ? "text-white bg-[#022733]"
            : "text-neutral-800 bg-none"
        }
        `}
          onClick={() => navigate("register")}
        >
          등록
        </button>
        <button
          className={`py-0.5 px-2 font-medium bg-none rounded-lg cursor-pointer
        ${
          location.pathname === "/admin/edit"
            ? "text-white bg-[#022733]"
            : "text-neutral-800 bg-none"
        }
        `}
          onClick={() => navigate("edit")}
        >
          수정/삭제
        </button>
      </nav>
      <Outlet />
    </main>
  );
};

export default AdminMain;
