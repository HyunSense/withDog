import {useEffect, useState} from "react";

const useDelayedLoader = (isLoading, delay = 300) => {

    const [showLoader, setShowLoader] = useState(false);

    useEffect(() => {
        let timeout;

        if (isLoading) {
            timeout = setTimeout(() => {
                setShowLoader(true);
            }, delay);
        } else {
            clearTimeout(timeout);
            setShowLoader(false);
        }

        return () => {
            clearTimeout(timeout);
        }
    }, [isLoading, delay]);

    return showLoader;

}

export default useDelayedLoader;
