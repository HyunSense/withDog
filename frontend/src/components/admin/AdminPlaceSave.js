import { useState } from "react";
import { postPlaces } from "../../apis/place";
import AdminPlaceForm from "./AdminPlaceForm";

const AdminPlaceSave = () => {
  // const navigate = useNavigate();
  const [formKey, setFormKey] = useState(0);

  const handleSave = async (formData) => {
    try {
      await postPlaces(formData);
      alert("장소가 등록 되었습니다.");
      setFormKey((prev) => prev + 1);
      // navigate("/admin/edit");
    } catch (error) {
      console.log("Save Failed: ", error);
      alert("장소 등록에 실패하였습니다.");
    }
  };

  return (
    <AdminPlaceForm
      key={formKey}
      initValues={{}}
      isEdit={false}
      onSubmit={handleSave}
    />
  );
};

export default AdminPlaceSave;
