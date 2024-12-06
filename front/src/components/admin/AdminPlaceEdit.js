import { useEffect, useState } from "react";
import { getPlace, updatePlaces } from "../apis/place";
import AdminPlaceForm from "./AdminPlaceForm";
import AdminPlaceFormCopy from "./AdminPlaceForm copy"
import { useNavigate, useParams } from "react-router-dom";
import Loading from "../common/Loading";

const AdminPlaceEdit = () => {
  const navigate = useNavigate();
  const { id } = useParams();
  const [place, setPlace] = useState(null);
  const [loading, setLoading] = useState(true);
  // const navigate = useNavigate();

  const fetchPlaceDetail = async (id) => {
    try {
      const response = await getPlace(id);
      console.log("Fetched place data = ", response.data.data);
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
    try {
      const response = await updatePlaces(place.id, formData);
      console.log("Update Success: ", response);
      // navigate("/admin/edit");
    } catch (error) {
      console.log("Update Failed: ", error);
    }
  };

  if (loading) {
    return <Loading />;
  }

  return (
    <AdminPlaceFormCopy initValues={place} isEdit={true} onSubmit={handleEdit} />
    // <AdminPlaceForm initValues={place} isEdit={true} onSubmit={handleEdit} />
  );
};

export default AdminPlaceEdit;
