import { useEffect, useState } from "react";
import { Button, Container, Form, Nav, Navbar, NavDropdown, } from "react-bootstrap";
import Apis, { endpoints } from "../configs/Apis";
import MySpinner from "./MySpinner";
import { useLocation, useNavigate } from "react-router-dom";

const Header = () => {

    const location = useLocation();
    const navigate = useNavigate();

    const [categoryTypes, setCategoryTypes] = useState(null);
    const [isLoggedIn, setIsLoggedIn] = useState(false);
    const [kw, setKw] = useState("");

    const loadCategoryTypes = async () => {
        try {
            let res = await Apis.get(endpoints["category-types"]);
            setCategoryTypes(res.data);

        } catch (error) {
            console.error("Failed to fetch category types:", error);
            setCategoryTypes([]);
        }
    };

    useEffect(() => {
        const token = localStorage.getItem("token");
        setIsLoggedIn(!!token); // true nếu có token
    }, []);

    useEffect(() => {
        loadCategoryTypes();
    }, []);

    const handleSearch = (e) => {
        e.preventDefault();
        const searchParams = new URLSearchParams(location.search);
        if (kw.trim()) {
            searchParams.set("kw", kw);
        } else {
            searchParams.delete("kw");
        }
        searchParams.set("page", "1"); // Reset to page 1 on new search
        navigate(`${location.pathname}?${searchParams.toString()}`);
        setKw("");
    };


    if (categoryTypes === null) {
        return <MySpinner />;
    }

    return (
        <>
            <Navbar expand="lg" className="bg-body-tertiary">
                <Container>
                    <Navbar.Brand href="/">Netix</Navbar.Brand>
                    <Navbar.Toggle aria-controls="basic-navbar-nav" />
                    <Navbar.Collapse id="basic-navbar-nav">
                        <Nav className="me-auto">
                            <NavDropdown title="Danh mục" id="basic-nav-dropdown">
                                {categoryTypes.map(c => (
                                    <NavDropdown.Item href="#link" key={c.id}>
                                        {c.name}
                                    </NavDropdown.Item>
                                ))}
                            </NavDropdown>
                            <NavDropdown title="Kĩ năng" id="basic-nav-dropdown">
                                <NavDropdown.Item onClick={() => navigate("/lessons?skill=listening")}>
                                    Listening
                                </NavDropdown.Item>
                                <NavDropdown.Item onClick={() => navigate("/lessons?skill=reading")}>
                                    Reading
                                </NavDropdown.Item>
                                <NavDropdown.Item onClick={() => navigate("/lessons?skill=speaking")}>
                                    Speaking
                                </NavDropdown.Item>
                                <NavDropdown.Item onClick={() => navigate("/lessons?skill=writing")}>
                                    Writing
                                </NavDropdown.Item>
                            </NavDropdown>
                            {isLoggedIn ? (
                                <>
                                    <Nav.Link href="/profile" className="btn btn-outline-primary me-2">Thông tin cá nhân</Nav.Link>
                                    <Nav.Link href="/logout" className="btn btn-outline-danger">Đăng xuất</Nav.Link>
                                </>
                            ) : (
                                <>
                                    <Nav.Link href="/login">Đăng nhập</Nav.Link>
                                    <Nav.Link href="/register">Đăng ký</Nav.Link>
                                </>
                            )}

                        </Nav>

                        <Form onSubmit={handleSearch} className="d-flex">
                            <Form.Control
                                type="search"
                                placeholder="Search"
                                className="me-2"
                                aria-label="Search"
                                name="kw"
                                value={kw}
                                onChange={(e) => setKw(e.target.value)}
                            />
                            <Button type="submit" variant="outline-primary">Tìm</Button>
                        </Form>
                    </Navbar.Collapse>
                </Container>
            </Navbar>
        </>
    );
}

export default Header;