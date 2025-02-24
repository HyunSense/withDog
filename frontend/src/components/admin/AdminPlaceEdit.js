import { useEffect, useState } from "react";
import { getAdminPlace, updatePlaces } from "../../apis/place";
import AdminPlaceForm from "./AdminPlaceForm";
import { useNavigate, useParams } from "react-router-dom";
import Loading from "../common/Loading";

const AdminPlaceEdit = () => {
  const navigate = useNavigate();
  const { id } = useParams();
  const [place, setPlace] = useState(null);
  const [loading, setLoading] = useState(true);

  const fetchPlaceDetail = async (id) => {
    try {
      const response = await getAdminPlace(id);
      setPlace(response.data.data);
    } catch (error) {
      console.error("Failed to fetch place details: ", error);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchPlaceDetail(id);
  }, [id]);

  const handleEdit = async (formData) => {
    setLoading(true);
    try {
      await updatePlaces(place.id, formData);
      alert("수정 되었습니다.");
      navigate("/admin/edit");
    } catch (error) {
      console.log("Update Failed: ", error);
      alert("수정에 실패하였습니다.");
    } finally {
      setLoading(false);
    }
  };

  if (loading) {
    return <Loading />;
  }

  return (
    <AdminPlaceForm initValues={place} isEdit={true} onSubmit={handleEdit} />
  );
};

export default AdminPlaceEdit;
