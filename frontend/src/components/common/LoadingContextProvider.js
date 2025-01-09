import { createContext, useContext, useState } from "react";

export const LoadingContext = createContext();

export const useLoading = () => useContext(LoadingContext);

export const LoadingProvider = ({children}) => {

    const [loading, setLoading] = useState(false);

    const setLoadingState = (state) => {
        console.log("로딩중..")
        setLoading(state);
    };

    return (
        <LoadingContext.Provider value={{loading, setLoadingState}}>
            {children}
        </LoadingContext.Provider>
    )
}