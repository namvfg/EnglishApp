import { Form, Button, Card, Container } from "react-bootstrap";
import { useState } from "react";
import { Link } from "react-router-dom";
import Apis, { endpoints } from "../../configs/Apis";
import MySpinner from "../../layout/MySpinner";

const Login = () => {
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [error, setError] = useState("");
    const [loading, setLoading] = useState(false);

    const handleSubmit = async (e) => {
        e.preventDefault();
        setLoading(true);
        try {
            const res = await Apis.post(endpoints["login"], {
                email,
                password
            });

            alert("Đăng nhập thành công!");
            window.location.href = "/";

            localStorage.setItem("userId", res.data.id);
            localStorage.setItem("token", res.data.token);
            localStorage.setItem("role", res.data.role);
            localStorage.setItem("email", res.data.email);
            localStorage.setItem("avatar", res.data.avatar);
            localStorage.setItem("firstName", res.data.firstName);
            localStorage.setItem("lastName", res.data.lastName);
        }
        catch (err) {
            if (err.response && err.response.data && err.response.data.error) {
                setError(err.response.data.error);
            } else {
                setError("Đăng nhập thất bại. Vui lòng thử lại.");
            }
        }
        finally {
            setLoading(false);
        }
        console.log("Đăng nhập với:", { email, password });
    };

    if (loading) {
        return <MySpinner />;
    }

    return (
        <Container className="d-flex justify-content-center align-items-center" style={{ minHeight: "70vh" }}>
            <Card style={{ width: "100%", maxWidth: "400px" }} className="p-4 shadow">
                <h3 className="mb-4 text-center">Đăng nhập</h3>
                <Form onSubmit={handleSubmit}>
                    <Form.Group className="mb-3" controlId="formEmail">
                        <Form.Label>Email</Form.Label>
                        <Form.Control
                            type="email"
                            placeholder="Nhập email"
                            value={email}
                            onChange={(e) => setEmail(e.target.value)}
                            required
                        />
                    </Form.Group>

                    <Form.Group className="mb-4" controlId="formPassword">
                        <Form.Label>Mật khẩu</Form.Label>
                        <Form.Control
                            type="password"
                            placeholder="Nhập mật khẩu"
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                            required
                        />
                    </Form.Group>

                    <Button type="submit" variant="primary" className="w-100">Đăng nhập</Button>
                    {error && <p style={{ color: "red" }}>{error}</p>}
                </Form>

                <div className="mt-3 text-center">
                    Chưa có tài khoản? <Link to="/register">Đăng ký</Link>
                </div>
            </Card>
        </Container>
    );
};

export default Login;