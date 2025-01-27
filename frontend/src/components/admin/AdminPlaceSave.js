import { postPlaces } from "../../apis/place";
import AdminPlaceForm from "./AdminPlaceForm";
import { useNavigate } from "react-router-dom";

const AdminPlaceSave = () => {
  const navigate = useNavigate();

  const handleSave = async (formData) => {
    try {
      await postPlaces(formData);
      alert("장소가 등록 되었습니다.");
      navigate("/admin/edit");
    } catch (error) {
      console.log("Save Failed: ", error);
      alert("장소 등록에 실패하였습니다.");
    } 
  };

  return (
    <AdminPlaceForm initValues={{}} isEdit={false} onSubmit={handleSave} />
  );
};

export default AdminPlaceSave;
