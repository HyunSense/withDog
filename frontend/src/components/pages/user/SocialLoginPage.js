import { useContext, useEffect } from "react";
import { useNavigate, useSearchParams } from "react-router-dom";
import { getMemberInfo } from "../../../apis/auth";
import { AuthContext } from "../../auth/AuthContextProvider";

const SocialLoginPage = () => {
  const { setIsLogin, setMemberInfo } = useContext(AuthContext);
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();
  const accessToken = searchParams.get("accessToken");

  const fetchMemberInfo = async (accessToken) => {
    try {
      const response = await getMemberInfo(accessToken);
      const redirectResponse = response.data.data;
      const memberInfo = {
        username: redirectResponse.username,
        role: redirectResponse.role,
      };

      localStorage.setItem("access_token", accessToken);
      localStorage.setItem("member_info", JSON.stringify(memberInfo));
      setIsLogin(true);
      setMemberInfo(JSON.stringify(memberInfo));

      navigate("/");
    } catch (error) {
      console.log("fetchMemberInfo Error = ", error);
    }
  };

  useEffect(() => {
    fetchMemberInfo(accessToken);
  });
};

export default SocialLoginPage;
