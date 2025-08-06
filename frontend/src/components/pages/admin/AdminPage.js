import AdminHeader from "../../admin/AdminHeader";
import AdminMain from "../../admin/AdminMain";

const AdminPage = () => {
  return (
    <div className="flex flex-col h-screen select-none">
      <AdminHeader />
      <AdminMain />
    </div>
  );
};

export default AdminPage;
