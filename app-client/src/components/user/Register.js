import { useState } from "react";
import { Form, Button, Card, Container, Row, Col } from "react-bootstrap";
import { useNavigate } from "react-router-dom";
import Apis, { endpoints } from "../../configs/Apis";
import MySpinner from "../../layout/MySpinner";

const Register = () => {
    const navigate = useNavigate();

    const [form, setForm] = useState({
        firstName: "",
        lastName: "",
        email: "",
        password: "",
        confirmPassword: "",
    });

    const [avatarFile, setAvatarFile] = useState(null);
    const [error, setError] = useState("");
    const [loading, setLoading] = useState(false);

    const handleChange = (e) => {
        setForm({ ...form, [e.target.name]: e.target.value });
    };

    const handleAvatarChange = (e) => {
        setAvatarFile(e.target.files[0]);
    };

    const handleRequestOtp = async (e) => {
        e.preventDefault();
        setError("");

        if (!form.firstName.trim() || !form.lastName.trim()) {
            setError("Họ và tên không được để trống!");
            return;
        }

        if (!form.email.match(/^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$/)) {
            setError("Email không hợp lệ!");
            return;
        }

        if (form.password.length < 6) {
            setError("Mật khẩu phải có ít nhất 6 ký tự!");
            return;
        }

        if (!/(?=.*[A-Za-z])(?=.*\d)/.test(form.password)) {
            setError("Mật khẩu phải chứa cả chữ và số!");
            return;
        }

        if (form.password !== form.confirmPassword) {
            setError("Mật khẩu không khớp!");
            return;
        }

        if (!avatarFile) {
            setError("Vui lòng chọn ảnh đại diện!");
            return;
        }

        if (form.password !== form.confirmPassword) {
            setError("Mật khẩu không khớp!");
            return;
        }

        setLoading(true);
        try {
            await Apis.post(endpoints["request-otp"], null, {
                params: { email: form.email }
            });

            navigate("/verify-otp", {
                state: {
                    form,
                    avatarFile
                }
            });
        } catch (err) {
            console.error("Lỗi gửi OTP:", err);
            if (err.response?.data?.error)
                setError(`${err.response.data.error}`);
            else
                setError("Gửi OTP thất bại!");
        } finally {
            setLoading(false);
        }
    };

    if (loading) return <MySpinner />;

    return (
        <Container className="d-flex justify-content-center align-items-center" style={{ minHeight: "70vh" }}>
            <Card style={{ width: "100%", maxWidth: "500px" }} className="p-4 shadow">
                <h3 className="mb-4 text-center">Đăng ký tài khoản</h3>
                <Form onSubmit={handleRequestOtp}>
                    <Row>
                        <Col>
                            <Form.Group className="mb-3">
                                <Form.Label>Họ</Form.Label>
                                <Form.Control type="text" name="firstName" value={form.firstName} onChange={handleChange} required />
                            </Form.Group>
                        </Col>
                        <Col>
                            <Form.Group className="mb-3">
                                <Form.Label>Tên</Form.Label>
                                <Form.Control type="text" name="lastName" value={form.lastName} onChange={handleChange} required />
                            </Form.Group>
                        </Col>
                    </Row>

                    <Form.Group className="mb-3">
                        <Form.Label>Email</Form.Label>
                        <Form.Control type="email" name="email" value={form.email} onChange={handleChange} required />
                    </Form.Group>

                    <Form.Group className="mb-3">
                        <Form.Label>Mật khẩu</Form.Label>
                        <Form.Control type="password" name="password" value={form.password} onChange={handleChange} required />
                    </Form.Group>

                    <Form.Group className="mb-3">
                        <Form.Label>Xác nhận mật khẩu</Form.Label>
                        <Form.Control type="password" name="confirmPassword" value={form.confirmPassword} onChange={handleChange} required />
                    </Form.Group>

                    <Form.Group className="mb-4">
                        <Form.Label>Ảnh đại diện</Form.Label>
                        <Form.Control type="file" accept="image/*" onChange={handleAvatarChange} required />
                    </Form.Group>

                    <Button type="submit" variant="success" disabled={loading} className="w-100">
                        {loading ? (<>
                            <span className="spinner-border spinner-border-sm me-2" role="status" aria-hidden="true"></span>
                            Đang gửi...
                        </>) : "Gửi mã OTP"}
                    </Button>
                    {error && <p style={{ color: "red" }} className="mt-2">{error}</p>}
                </Form>
            </Card>
        </Container>
    );
};

export default Register;
