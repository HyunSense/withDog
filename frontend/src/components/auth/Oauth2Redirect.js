import { useCallback, useContext, useEffect } from "react";
import { useNavigate, useSearchParams } from "react-router-dom";
import { AuthContext } from "./AuthContextProvider";

const Oauth2Redirect = () => {
  const { setIsLogin, setMemberInfo } = useContext(AuthContext);
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();
  const accessToken = searchParams.get("accessToken");
  const username = searchParams.get("username");
  const role = searchParams.get("role");


  const error = searchParams.get("error");
  const message = searchParams.get("message");

  const handleSuccess = useCallback(() => {
    const memberInfo = {
      username: username,
      role: role,
    };

    localStorage.setItem("access_token", accessToken);
    localStorage.setItem("member_info", JSON.stringify(memberInfo));
    setIsLogin(true);
    setMemberInfo(JSON.stringify(memberInfo));

    navigate("/");
  }, [accessToken, username, role, setIsLogin, setMemberInfo, navigate]);

  const handleFailure = useCallback(() => {

    alert(`로그인 실패: ${message || "알 수 없는 로그인 오류 발생"}`);
    navigate("/login");
  },[message, navigate]);

  useEffect(() => {
    if (error) {
      handleFailure();
    } else if (accessToken) {
      handleSuccess();
    }
  }, [accessToken, error, handleSuccess, handleFailure]);

  return null;
};

export default Oauth2Redirect;
