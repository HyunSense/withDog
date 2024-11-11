import React, { createContext, useEffect, useState } from "react";

export const AuthContext = createContext();

export const AuthProvider = ({children}) => {

    const [isAuthenticated, setIsAuthenticated] = useState(false);
    const [username, setUsername] = useState("");

    useEffect(() => {
        // async 가 반드시 필요하지 않지만 서버에서 추가적인 정보를 가져올수도있기 때문에 작성
        const checkAuthStatus = async() => {
            const token = localStorage.getItem("access_token");
            if (token) {
                try {
                    const decodedToken = 
                }
            }
        }
    })
}