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

// PUT Places
export const updatePlaces = (placeId, data) =>
  api.put(`/places/${placeId}`, data, {
    headers: { "Content-Type": "multipart/form-data" },
  });

// DELETE Places
export const deletePlaces = (params) =>
  api.delete("/places", { params: params });

// GET BookmarkStatus
export const getBookmarkStatus = (placeId) =>
  api.get(`/places/${placeId}/bookmarks/status`);

// GET AllBookmarks
export const getAllBookmarks = () => api.get("/places/bookmarks");

// POST AddBookmark
export const addBookmark = (placeId) =>
  api.post(`/places/${placeId}/bookmarks`);

// DELETE DeleteBookmark
export const deleteBookmark = (placeId) =>
  api.delete(`/places/${placeId}/bookmarks`);

// DELETE DeleteSelectedBookmarks
export const deleteSelectedBookmarks = (params) =>
  api.delete("/places/bookmarks", { params: params });

// GET Top3Places
export const getTop3Places = (params) =>
  api.get("/places/top3", { params: params });

// GET SearchPlaces
export const getSearchPlaces = (params) =>
  api.get("/places/search/result", { params: params });

// GET SearchCountPlaces
export const getSearchCountPlaces = (params) =>
  api.get("/places/search/result/count", { params: params });

// GET RecentPlaces
export const getRecentPlaces = (params) =>
  api.get("/places/recent", { params: params });

// GET RandomPlaces
export const getRandomPlaces = (params) =>
  api.get("/places/random", { params: params });

// GET AdminPlace
export const getAdminPlace = (id) => api.get(`/admin/places/${id}`);

// GET CountPlaces
export const getCountPlaces = () => api.get("/places/count");
