import { useContext, useEffect } from "react";
import { useNavigate, useSearchParams } from "react-router-dom";
import { AuthContext } from "../../auth/AuthContextProvider";

const SocialLoginPage = () => {
  const { setIsLogin, setMemberInfo } = useContext(AuthContext);
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();
  const accessToken = searchParams.get("accessToken");
  const username = searchParams.get("username");
  const role = searchParams.get("role");

  const handleSocialMember = async () => {

      const memberInfo = {
        username: username,
        role: role,
      };

      localStorage.setItem("access_token", accessToken);
      localStorage.setItem("member_info", JSON.stringify(memberInfo));
      setIsLogin(true);
      setMemberInfo(JSON.stringify(memberInfo));

      navigate("/");
  };

  useEffect(() => {
    handleSocialMember();
  });
};

export default SocialLoginPage;
