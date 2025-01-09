import styled from "styled-components";
import AdminHeader from "../../admin/AdminHeader";
import AdminMain from "../../admin/AdminMain";

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
    <StyledAdminPage>
      <AdminHeader />
      <AdminMain />
    </StyledAdminPage>
  );
};

export default AdminPage;
