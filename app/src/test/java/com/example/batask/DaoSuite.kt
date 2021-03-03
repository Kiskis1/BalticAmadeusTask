package com.example.batask

import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite::class)
@Suite.SuiteClasses(
    PostDaoTest::class,
    UserDaoTest::class
)
class DaoSuite