import { useEffect, useState } from "react";
import { useSearchParams } from "react-router-dom";
import { Container, Row, Col, Button, Card, Pagination } from "react-bootstrap";
import Apis, { endpoints } from "../../configs/Apis";
import MySpinner from "../../layout/MySpinner";

const LessonList = () => {
  const [lessons, setLessons] = useState([]);
  const [totalLessons, setTotalLessons] = useState(0);
  const [searchParams, setSearchParams] = useSearchParams();
  const [loading, setLoading] = useState(false);

  const kw = searchParams.get("kw") || "";
  const page = parseInt(searchParams.get("page")) || 1;
  const skill = searchParams.get("skill") || "";

  const pageSize = 6;

  useEffect(() => {
    const loadLessons = async () => {
      setLoading(true);
      try {
        const res = await Apis.get(endpoints["lessons"], {
          params: { page, kw, skill },
        });
        if (res.status === 204) {
          setLessons([]); // nếu backend trả về no content
        } else if (Array.isArray(res.data)) {
          setLessons(res.data);
        } else {
          setLessons([]);
        }
      } catch (err) {
        console.error(err);
        setLessons([]);
      } finally {
        setLoading(false);
      }
    };

    loadLessons();
  }, [page, kw, skill]);

  useEffect(() => {
    const count = async () => {
      try {
        const res = await Apis.get(endpoints["lessons-count"], {
          params: { kw, skill },
        });
        setTotalLessons(res.data);
      } catch (err) {
        console.error(err);
      }
    };
    count();
  }, [kw, skill]);

  const totalPages = Math.ceil(totalLessons / pageSize);

  const goToPage = (p) => {
    setSearchParams({ page: p, kw, skill });
  };

  return (
    <Container className="my-4">
      <h2 className="mb-4">Danh sách bài học</h2>

      {loading ? (
        <MySpinner />
      ) : (lessons.length === 0 ? (
        <p className="text-center">Không có bài học nào phù hợp.</p>
      ) : (<Row>
        {lessons.map((l) => (
          <Col md={4} className="mb-4" key={l.id}>
            <Card className="h-100 shadow-sm">
              <Card.Img
                variant="top"
                src={l.image}
                alt={l.title}
                style={{ height: "200px", objectFit: "cover" }}
              />
              <Card.Body>
                <Card.Title>{l.title}</Card.Title>
                <Card.Text>
                  <strong>Chuyên mục:</strong> {l.categoryName} <br />
                  <strong>Loại bài:</strong> {l.lessonTypeName}
                </Card.Text>
                <Button href={`/lessons/${l.id}`} variant="primary">
                  Làm bài
                </Button>
              </Card.Body>
            </Card>
          </Col>
        ))}
      </Row>)
      )}

      {totalPages > 1 && (
        <Pagination className="justify-content-center mt-4">
          {Array.from({ length: totalPages }, (_, i) => (
            <Pagination.Item
              key={i + 1}
              active={i + 1 === page}
              onClick={() => goToPage(i + 1)}
            >
              {i + 1}
            </Pagination.Item>
          ))}
        </Pagination>
      )}
    </Container>
  );
};

export default LessonList;
