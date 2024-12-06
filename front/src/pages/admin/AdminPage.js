import styled from "styled-components";
import AdminHeader from "../../components/admin/AdminHeader";
import AdminMain from "../../components/admin/AdminMain";
import { AdminProvider } from "../../components/admin/AdminContextProvider";

const StyledAdminPage = styled.div`
  display: flex;
  flex-direction: column;
  height: 100vh;

  -webkit-user-select: none;
  -moz-user-select: none;
  -ms-user-select: none;
  user-select: none;
`;

const AdminPage = () => {
  return (
    <AdminProvider>
      <StyledAdminPage>
        <AdminHeader />
        <AdminMain />
      </StyledAdminPage>
    </AdminProvider>
  );
};

export default AdminPage;
