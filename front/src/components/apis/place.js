import api from "./api";

// GET AllPlaces
export const getAllPlaces = (params) => api.get("/places", { params: params });

// GET Places
export const getPlace = (id) => api.get(`/places/${id}`);

// POST Places
export const postPlaces = (data) =>
  api.post("/places", data, {
    headers: { "Content-Type": "multipart/form-data" },
  });

// GET BookmarkStatus
export const getBookmarkStatus = (placeId) =>
  api.get(`/places/${placeId}/bookmarks/status`);

// POST AddBookmark
export const addBookmark = (placeId) =>
  api.post(`/places/${placeId}/bookmarks`);

// DELETE DeleteBookmark
export const deleteBookmark = (placeId) =>
  api.delete(`/places/${placeId}/bookmarks`);

// GET Top3Places
export const getTop3Places = (params) =>
  api.get("/places/top3", { params: params });
