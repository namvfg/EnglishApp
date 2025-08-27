import { useEffect, useState } from "react";
import MySpinner from "../layout/MySpinner";
import Apis, { endpoints } from "../configs/Apis";
import { Button, Card, Col, Row } from "react-bootstrap";
import { useSearchParams } from "react-router-dom";

const Home = () => {

  const [categories, setCategories] = useState(null);
  const [q] = useSearchParams();

  useEffect(() => {
    const loadCategories = async () => {

      try {
        let e = endpoints["categories"];
        if (q.get("kw")) {
          e += `?kw=${q.get("kw")}`;
        }
        let res = await Apis.get(e);

        if (res.status === 204) {
          setCategories([]); // nếu backend trả về no content
        } else if (Array.isArray(res.data)) {
          setCategories(res.data);
        } else {
          setCategories([]);
        }
      } catch (error) {
        console.error("Failed to fetch categories:", error);
        setCategories([]);
      }
    }
    loadCategories();
  }, [q]);

  if (categories === null) {
    return <MySpinner />;
  }


  return (
    <>
      <h2>Categories</h2>
      <Row>
        {categories.length === 0 ? (
          <p className="text-center">Không có danh mục nào.</p>
        ) : (
          categories.map(category => (
            <Col xs={12} md={3} key={category.id} className="mb-4">
              <Card style={{ width: '18rem' }}>
                <Card.Img
                  variant="top"
                  src="https://res.cloudinary.com/dcee16rsp/image/upload/v1755430567/Ielts_wrpwd7.jpg"
                  style={{ height: "200px", objectFit: "cover" }}
                />
                <Card.Body>
                  <Card.Title>{category.name}</Card.Title>
                  <Card.Text>{category.categoryName}</Card.Text>
                  <Button variant="primary">Làm bài</Button>
                </Card.Body>
              </Card>
            </Col>
          ))
        )}
      </Row>
    </>
  );
}

export default Home;