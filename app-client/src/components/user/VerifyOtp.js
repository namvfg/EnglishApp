import { useState } from "react";
import { Form, Button, Card, Container } from "react-bootstrap";
import { useLocation, useNavigate } from "react-router-dom";
import Apis, { endpoints } from "../../configs/Apis";

const VerifyOtp = () => {
    const navigate = useNavigate();
    const [loading, setLoading] = useState(false);
    const location = useLocation();
    const [otp, setOtp] = useState("");
    const [error, setError] = useState("");

    const { form, avatarFile } = location.state || {};

    const handleVerify = async (e) => {
        e.preventDefault();
        setError("");

        try {
            setLoading(true);

            await Apis.post(endpoints["verify-otp"], null, {
                params: {
                    email: form.email,
                    otp: otp
                }
            });

            const formData = new FormData();
            formData.append("first_name", form.firstName);
            formData.append("last_name", form.lastName);
            formData.append("email", form.email);
            formData.append("password", form.password);
            formData.append("image", avatarFile);

            const res = await Apis.post(endpoints["register"], formData, {
                headers: { "Content-Type": "multipart/form-data" },
            });

            alert(res.data.message || "Đăng ký thành công!");
            navigate("/login");
        } catch (err) {
            console.error("Lỗi xác minh hoặc đăng ký:", err);
            if (err.response?.data?.error) {
                setError(`${err.response.data.error}`);
            } else {
                setError("Xác minh OTP hoặc đăng ký thất bại!");
            }
        } finally {
            setLoading(false);
        }
    };

    if (!form || !avatarFile) {
        return <p className="text-center mt-5 text-danger">Thiếu thông tin đăng ký. Vui lòng quay lại trang đăng ký.</p>;
    }

    return (
        <Container className="d-flex justify-content-center align-items-center" style={{ minHeight: "70vh" }}>
            <Card style={{ width: "100%", maxWidth: "400px" }} className="p-4 shadow">
                <h3 className="mb-4 text-center">Xác minh OTP</h3>
                <Form onSubmit={handleVerify}>
                    <Form.Group className="mb-3">
                        <Form.Label>Nhập mã OTP</Form.Label>
                        <Form.Control type="text" value={otp} onChange={(e) => setOtp(e.target.value)} required />
                    </Form.Group>
                    <Button type="submit" variant="primary" disabled={loading} className="w-100">
                        {loading ? (
                            <>
                                <span className="spinner-border spinner-border-sm me-2" role="status" aria-hidden="true"></span>
                                Đang xác minh...
                            </>
                        ) : "Xác minh và đăng ký"}
                    </Button>
                    {error && <p style={{ color: "red" }}>{error}</p>}
                </Form>
            </Card>
        </Container>
    );
};

export default VerifyOtp;