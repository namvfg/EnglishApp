import { useEffect } from "react";
import { useNavigate } from "react-router-dom";

const Logout = () => {
    const navigate = useNavigate();

    useEffect(() => {
        localStorage.removeItem("token");
        localStorage.removeItem("role");
        localStorage.removeItem("registerForm");
        localStorage.removeItem("avatarFile");

        alert("Đăng xuất thành công!");
        navigate("/login");
    }, [navigate]);

    return null;
};

export default Logout;
