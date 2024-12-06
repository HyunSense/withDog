import { postPlaces } from "../apis/place";
import AdminPlaceForm from "./AdminPlaceForm";
import AdminPlaceFormCopy from "./AdminPlaceForm copy";


const AdminSave2 = () => {
  const handleSave = async (formData) => {
    try {
      const response = await postPlaces(formData);
      console.log("Save Success: ", response);
    } catch (error) {
      console.log("Save Failed: ", error);
    }
  };

  return (
    // <AdminPlaceForm initValues={{}} isEdit={false} onSubmit={handleSave} />
    // <AdminPlaceFormCopy initValues={{}} isEdit={false} onSubmit={handleSave} />
    <AdminPlaceFormCopy initValues={{}} isEdit={false} onSubmit={handleSave} />
  );
};

export default AdminSave2;
