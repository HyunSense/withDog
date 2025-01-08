import { useState } from "react";
import { postPlaces } from "../../apis/place";
import AdminPlaceForm from "./AdminPlaceForm";
import Loading from "../common/Loading";
import { useNavigate } from "react-router-dom";

const AdminPlaceSave = () => {
  const navigate = useNavigate();
  const [loading, setLoading] = useState(false);

  const handleSave = async (formData) => {
    setLoading(true);
    try {
      const response = await postPlaces(formData);
      console.log("Save Success: ", response);
      alert("장소가 등록 되었습니다.");
      navigate("/admin/edit");
    } catch (error) {
      console.log("Save Failed: ", error);
      alert("장소 등록에 실패하였습니다.");
    } finally {
      setLoading(false);
    }
  };

  if (loading) {
    return <Loading />;
  }

  return (
    <AdminPlaceForm initValues={{}} isEdit={false} onSubmit={handleSave} />
  );
};

export default AdminPlaceSave;
